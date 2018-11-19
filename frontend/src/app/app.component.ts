import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface IResult {
  miningResult: {
   results: [{
     template: string,
     eventA: {
       attributes: [{
         key: string,
         value: string
       }]
     },
     eventB: {
      attributes: [{
        key: string,
        value: string
      }]
    },
     n: number,
     support: number,
     confidence: number
   }]
  }
  pivotLogger: {}
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  /**
   * table
   */
  displayedColumns: string[] = ['template', 'eventA', 'eventB', 'n', 'support', 'confidence'];
  dataSource 

   miningResult = {} as IResult


  constructor(
    private httpClient: HttpClient
  ) {

    const headers = new HttpHeaders({ 'Content-Type': 'application/xml' });

    httpClient.post<IResult>('http://localhost:8080/miningJob', 
    '<?xml version="1.0" encoding="UTF-8"?><eventLog><trace>		<event>			<string key="task" value="a"/>			<string key="resource" value="x"/>		</event>		<event>			<string key="task" value="c"/>			<string key="resource" value="z"/>		</event>		<event>			<string key="task" value="b"/>			<string key="resource" value="y"/>		</event>		<event>			<string key="task" value="b"/>			<string key="resource" value="x"/>		</event>		<event>			<string key="task" value="d"/>			<string key="resource" value="y"/>		</event>		<event>			<string key="task" value="b"/>			<string key="resource" value="y"/>		</event>		<event>			<string key="task" value="a"/>			<string key="resource" value="x"/>		</event>	</trace>    <number>100</number></eventLog>',
    {headers: headers}).subscribe(
      result => {
        console.log(result);
        this.miningResult = result;
        this.dataSource = result.miningResult.results;
      }
    )
  }
}
