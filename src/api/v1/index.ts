import express from 'express';

import auth from './routes/auth';
import guild from './routes/guild';

const router = express.Router();

router.use('/auth', auth);
router.use('/guilds', guild);

export default router;
