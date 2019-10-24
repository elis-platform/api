export const errors = {
  validations: {
    required: 'ValidationError',
    invalid: {
      id: 'InvalidObjectId'
    }
  },
  authentication: {
    badpassword: 'InvalidPassword'
  },
  entity: {
    notfound: 'EntityNotFound'
  },
  database: {
    duplicate: 'ConflictError'
  },
  internal: {
    error: 'InternalError'
  }
};

export default errors;