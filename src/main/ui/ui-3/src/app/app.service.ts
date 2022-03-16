import { Injectable } from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AppService {
  constructor(private http: HttpClient) {
  }

  currentResource: any;

  rootURL = '/callbacks';

  getResources() {
    return this.http.get(this.rootURL + '/resources')
  }

  addResource(resource: any) {
    return this.http.post(this.rootURL + '/new-resource', resource);
  }

  removeResource(resource: any) {
    const params = new HttpParams()
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: resource
    };
    console.log(options)
    return this.http.delete(this.rootURL + '/remove-resource', options);
  }

  updateResource(resource: any) {
    console.log("About to go to callbacks/update-resource");
    return this.http.put(this.rootURL + '/update-resource', resource);
  }

  findResourceById(id: any) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: {id: id}
    }
    console.log(options)
    return this.http.get(this.rootURL + '/resource-by-id', options);
  }

  setCurrentResource(resource: any) {
    this.currentResource = resource;
  }

  getCurrentResource() {
    return this.currentResource;
  }
}
