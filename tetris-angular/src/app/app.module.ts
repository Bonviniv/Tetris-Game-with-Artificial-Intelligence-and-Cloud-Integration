import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { TetrisComponent } from './components/tetris/tetris.component';
import { TetrisService } from './services/tetris.service';

@NgModule({
  declarations: [
    AppComponent,
    TetrisComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [TetrisService],
  bootstrap: [AppComponent]
})
export class AppModule { }