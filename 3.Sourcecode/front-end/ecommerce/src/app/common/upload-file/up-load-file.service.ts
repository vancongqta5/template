import { Injectable } from '@angular/core';
import { AngularFireStorage, AngularFireStorageReference } from '@angular/fire/compat/storage';
import { forkJoin, map, Observable, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpLoadFileService {
  
  constructor(private storage: AngularFireStorage) { 
  }

  uploadFileAndGetDownloadUrl(file: File, folder: string): Observable<string> {
    const filePath = `uploads/${folder}/${file.name}`;
    const fileRef = this.storage.ref(filePath);
    const task = this.storage.upload(filePath, file);

    return new Observable<string>(observer => {
      task.snapshotChanges().subscribe(
        snapshot => {},
        error => {
          console.error(error);
          observer.error(error);
        },
        () => {
          fileRef.getDownloadURL().subscribe(
            url => {
              observer.next(url);
              observer.complete();
            },
            error => {
              console.error(error);
              observer.error(error);
            }
          );
        }
      );
    });
  }


  uploadFiles(files: File[]): Observable<string[]> {
    return this.storage.ref('uploads/').getDownloadURL().pipe(
      switchMap((folderUrl:any) => {
        const uploadTasks = files.map(file => {
          const filePath = `uploads/${file.name}`;
          const task = this.storage.upload(filePath, file);
          return task.snapshotChanges().pipe(
            map((snapshot:any) => snapshot.ref.getDownloadURL())
          );
        });
        return forkJoin(uploadTasks);
      })
    );
  }
}
