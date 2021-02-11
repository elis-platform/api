package com.venuses.manager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.venuses.manager.domain.application.guild.GuildApplicationService;
import com.venuses.manager.domain.application.guild.GuildApplicationServiceImpl;
import com.venuses.manager.domain.application.guild.GuildRepository;
import com.venuses.manager.domain.application.guild.dto.DiscordServerDto;
import com.venuses.manager.domain.application.guild.dto.GuildDto;
import com.venuses.manager.domain.application.member.MemberRepository;
import com.venuses.manager.domain.application.member.dto.MemberDto;
import com.venuses.manager.domain.application.playlist.PlaylistRepository;
import com.venuses.manager.domain.application.playlist.dto.PlaylistDto;
import com.venuses.manager.domain.core.guild.CreateGuildFactory;
import com.venuses.manager.domain.exception.GuildNotFoundException;
import com.venuses.manager.domain.exception.MemberDuplicatedException;
import com.venuses.manager.domain.exception.MemberNotFoundException;
import com.venuses.manager.setup.GuildRepositoryInMemoryImpl;
import com.venuses.manager.setup.MemberRepositoryInMemoryImpl;
import com.venuses.manager.setup.PlaylistRepositoryInMemoryImpl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManagerApplicationTests {

	private List<GuildDto> guilds = new ArrayList<>();
	private List<MemberDto> members = new ArrayList<>();
	private List<PlaylistDto> playlists = new ArrayList<>();

	private GuildDto guild;

	private GuildRepository guildRepository = new GuildRepositoryInMemoryImpl(guilds, members, playlists);
	private MemberRepository memberRepository = new MemberRepositoryInMemoryImpl(members);
	private PlaylistRepository playlistRepository = new PlaylistRepositoryInMemoryImpl(playlists);
	private CreateGuildFactory createGuildFactory = new CreateGuildFactory();
	private GuildApplicationService guildApplicationService = new GuildApplicationServiceImpl(guildRepository,
			createGuildFactory, memberRepository, playlistRepository);

	@Test
	void shouldCreateGuild() {
		DiscordServerDto discordServerDto = new DiscordServerDto("1", "Brazil", "John");
		GuildDto guildDto = new GuildDto("New Discord Server Test", "8712627182982", discordServerDto);
		guild = this.guildApplicationService.createGuild(guildDto);
		assertNotNull(guild);
		assertNotNull(guild.getId());
		assertNotNull(guild.getCreationDate());
	}

	@Test
	void shouldCreatePlaylist() throws GuildNotFoundException, MemberNotFoundException, MemberDuplicatedException {
		GuildDto guildRequest = new GuildDto(
			"Python Community",
			"98279878926",
			new DiscordServerDto("3", "Italy", "Guido")
		);
		guild = this.guildApplicationService.createGuild(guildRequest);
		assertNotNull(guild.getId());

		MemberDto memberRequest = new MemberDto("7356719871", "mellomaths", true);
		MemberDto member = this.guildApplicationService.addMember(guild.getId(), memberRequest);
		assertNotNull(member.getId());

		PlaylistDto playlistRequest = new PlaylistDto("Playlist of Rock", member.getId(), true, new ArrayList<>());
		PlaylistDto playlist = this.guildApplicationService.createPlaylist(guild.getId(), playlistRequest);
		assertNotNull(playlist.getId());
		assertNotNull(playlist.getCreationDate());

		guild = this.guildApplicationService.getGuild(guild.getId());
		assertTrue(guild.getPlaylists().contains(playlist));
	}

}
