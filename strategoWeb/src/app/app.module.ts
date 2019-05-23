import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms'
import { AppComponent } from './app.component';
import { SpelerComponent } from './speler/speler.component';
import { HttpClientModule} from '@angular/common/http';
import { SpelComponent } from './spel/spel.component';

@NgModule({
  declarations: [
    AppComponent,
    SpelerComponent,
    SpelComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
