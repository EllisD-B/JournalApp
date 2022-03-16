import * as createResourceActions from '../actions/CreateResource';
import { Action, createReducer, on } from '@ngrx/store';

export interface State {
  isLoading: boolean;
  isLoadingSuccess: boolean;
  resources: any;
};

const initialState: State = {
  isLoading: false,
  isLoadingSuccess: false,
  resources: []
};

export const createResourceReducer = createReducer(
  initialState,
  on(createResourceActions.createResource, state => ({...state, isLoading: true, isLoadingSuccess: false, login: undefined})),
  on(createResourceActions.createResourceSuccess, (state , resource) => ({...state, isLoading: false, isLoadingSuccess: true, resource})),
  on(createResourceActions.createResourceFailure, (state, resource) => ({...state, isLoading: false, isLoadingSuccess: true, resource}))
);

export function reducer(state: State | undefined, action: Action) {
  return createResourceReducer(state, action);
}

export const getResources = (state: State) => {
  return {
    isLoading: state.isLoading,
    isLoadingSuccess: state.isLoadingSuccess,
    login: state.resources };
};
