import express from 'express';
import bodyParser from 'body-parser';
import methodOverride from 'method-override';
import morgan from 'morgan';
import cors from 'cors';

import env from './env';

import auth from '../api/v1/auth';

/**
 * Extending the express.Request object
 * to include mongo userId in the application
 * and use this information globally through all routes
 */
declare global {
  namespace Express {
    interface Request {
      userId: string;
    }
  }
}

export const createExpressApplication = function() {
  const app: express.Application = express();

  // Enviroment setup
  app.set('port', env.port);

  // Middlewares
  app.use(morgan('combined'));
  app.use(cors());
  app.use(bodyParser.urlencoded({ extended: true }));
  app.use(bodyParser.json());
  app.use(methodOverride());

  // Routes
  app.use('/api/v1/auth', auth)

  return app;
};

export default createExpressApplication;