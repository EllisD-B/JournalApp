import { createAction, props } from '@ngrx/store';
import { Resource } from '../models';

export const CREATE_RESOURCE = '[CREATE RESOURCE] Create Resource API ';
export const CREATE_RESOURCE_SUCCESS =  '[CREATE RESOURCE] Create Resource API Success';
export const CREATE_RESOURCE_FAILURE = '[CREATE RESOURCE] Create Resource API Failure';

export const createResource = createAction(
  CREATE_RESOURCE,
  props<Resource>()
);

export const createResourceSuccess = createAction(
  CREATE_RESOURCE,
  props<Resource>()
);

export const createResourceFailure = createAction(
  CREATE_RESOURCE,
  props<Resource>()
);
