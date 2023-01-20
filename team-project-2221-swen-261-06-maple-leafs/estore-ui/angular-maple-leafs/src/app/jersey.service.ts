/**
 * JerseyService: service to connect to the JerseyAPI
 */

import { ComponentFactoryResolver, Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { Jersey } from './jersey';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Console } from 'console';

@Injectable({
  providedIn: 'root'
})

export class JerseyService {

  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})};
  private jerseysUrl = 'http://localhost:8080/jerseys';
  
  /**
   * When you instantiate a jersey service
   * @param http to be able to talk to API
   */
  constructor(private http: HttpClient) { }

  /**
   * Will fetch all the jerseys from the API
   * @returns observable of the jerseys list
   */
  getJerseys(): Observable<Jersey[]> {
    console.log("Fetched all jerseys!")
    return this.http.get<Jersey[]>(this.jerseysUrl);
  }

  /**
   * Will get specified jersey based on id
   * @param id the id of the jersey to get
   * @returns Observable of a jersey
   */
  getJersey(id: number): Observable<Jersey> {
    const url = `${this.jerseysUrl}/${id}`;
    console.log("Fetched jersey ID: " + id);
    return this.http.get<Jersey>(url);
  }


  deleteJersey(id: number): Observable<Jersey> {
    const url = `${this.jerseysUrl}/${id}`;
    return this.http.delete<Jersey>(url, this.httpOptions);
  }
  
  addJersey(jersey: Jersey): Observable<Jersey> {
      return this.http.post<Jersey>(this.jerseysUrl, jersey, this.httpOptions).pipe(
        tap((newJersey: Jersey) => console.log(`added jersey w/id=${newJersey.id}`))
        );
  }


  editJersey(jersey: Jersey): Observable<Jersey> {
      return this.http.put<Jersey>(this.jerseysUrl, jersey, this.httpOptions).pipe(
        tap((newJersey: Jersey) => console.log(`edited jersey w/id=${newJersey.id}`))
        );
  }

  searchJerseys(term: string): Observable<Jersey[]> {
    if (term == null) {
      // if not search term, return empty hero array.
      // return of([]);
      console.log("hewwo");
      return this.http.get<Jersey[]>(this.jerseysUrl);
    }
    return this.http.get<Jersey[]>(`${this.jerseysUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found jerseys matching "${term}"`) :
         this.log(`no jerseys matching "${term}"`)),
      catchError(this.handleError<Jersey[]>('searchJerseys', []))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      //this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(message);
    //this.messageService.add(`HeroService: ${message}`);
  }
}
