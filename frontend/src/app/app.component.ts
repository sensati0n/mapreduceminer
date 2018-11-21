import { Component, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { IResult, IPivotInformation } from './interfaces/IResult';
import { ITrace } from './interfaces/ITrace';
import { DOCUMENT } from '@angular/common';
import { FormControl } from '@angular/forms';

interface IPivot {
  i: number,
  j: number
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

  displayedPivotColumns: string[] = ['i', 'j'];
  pivotDataSource

  pivotData 

  myControl = new FormControl();

  mock='<?xml version="1.0" encoding="UTF-8"?><eventLog><trace>        <event>            <string key="task" value="a"/>            <string key="resource" value="x"/>        </event>        <event>            <string key="task" value="c"/>            <string key="resource" value="z"/>        </event>        <event>            <string key="task" value="b"/>            <string key="resource" value="y"/>        </event>        <event>            <string key="task" value="b"/>            <string key="resource" value="x"/>        </event>        <event>            <string key="task" value="d"/>            <string key="resource" value="z"/>        </event>        <event>            <string key="task" value="b"/>            <string key="resource" value="y"/>        </event>        <event>            <string key="task" value="a"/>            <string key="resource" value="x"/>        </event>    </trace>    <trace>        <event>            <string key="task" value="a"/>            <string key="resource" value="x"/>        </event>        <event>            <string key="task" value="b"/>            <string key="resource" value="x"/>        </event>        <event>            <string key="task" value="b"/>            <string key="resource" value="y"/>        </event>        <event>            <string key="task" value="c"/>            <string key="resource" value="x"/>        </event>    </trace>	<trace>        <event>            <string key="task" value="a"/>            <string key="resource" value="y"/>        </event>        <event>            <string key="task" value="c"/>            <string key="resource" value="x"/>        </event>        <event>            <string key="task" value="d"/>            <string key="resource" value="y"/>        </event>    </trace>	<number>100</number></eventLog>'

 /* mock=`
  <?xml version="1.0" encoding="UTF-8"?>
  <eventLog>
    <trace>
      <event> 
        <string key="task" value="a"/>
        <string key="resource" value="x"/> 
      </event>   
      <event>      
        <string key="task" value="c"/> 
        <string key="resource" value="z"/>   
      </event>   
      <event>       
        <string key="task" value="b"/>       
        <string key="resource" value="y"/>     
      </event>    
      <event>    
        <string key="task" value="b"/>     
        <string key="resource" value="x"/>    
      </event>   
      <event>      
        <string key="task" value="d"/>    
        <string key="resource" value="z"/>     
      </event>   
      <event>     
        <string key="task" value="b"/> 
        <string key="resource" value="y"/>       
      </event>    
      <event>       
        <string key="task" value="a"/>        
        <string key="resource" value="x"/> 
      </event> 
    </trace>  
    <trace>  
      <event>        
        <string key="task" value="a"/>            
        <string key="resource" value="x"/>   
      </event>    
      <event>     
        <string key="task" value="b"/>   
        <string key="resource" value="x"/>    
      </event>  
      <event>         
        <string key="task" value="b"/>        
        <string key="resource" value="y"/>    
      </event>   
      <event>         
        <string key="task" value="c"/>      
        <string key="resource" value="x"/>    
      </event> 
    </trace>	
    <trace>     
      <event>     
        <string key="task" value="a"/>    
        <string key="resource" value="y"/>    
      </event>    
      <event>         
        <string key="task" value="c"/>          
        <string key="resource" value="x"/>    
      </event>    
      <event>      
        <string key="task" value="d"/>   
        <string key="resource" value="y"/>     
      </event>  
    </trace>
    <number>1</number>
  </eventLog>
  `
*/
   miningResult = {} as IResult

   currentPivotInformation : IPivotInformation

  constructor(
    private httpClient: HttpClient,
  ) {

  }

  sendRequest() {
    const headers = new HttpHeaders({ 'Content-Type': 'application/xml' });

    this.httpClient.post<IResult>('http://localhost:8080/miningJob', 
    this.mock,
    {headers: headers}).subscribe(
      result => {
        console.log(result);
        this.miningResult = result;
        this.dataSource = result.miningResult.results;
        this.pivotData = result.pivotLogger.pivotInformation;
      }
    )
  }


  currentPds
  currentTrace
  onClickPds(pds) {
    console.log(pds);
    this.currentPds =pds;
    this.pivotDataSource = pds.ptti[0].pivotListActivation.pivots;
    this.currentTrace = pds.trace;
  

    
  }

  currentPivot = {i: 0, j: 0}

  
  currentDomPivotElementi
  currentDomPivotElementj


  markPivots(row) {
    
    console.log(row);
    this.currentPivot.i = row.i;
    this.currentPivot.j = row.j;

    if(this.currentDomPivotElementi) {
    this.currentDomPivotElementi.classList.remove('markRed')
    this.currentDomPivotElementj.classList.remove('markBlue')
  }
    this.currentDomPivotElementi = document.getElementById('pivot_' + row.i);
    this.currentDomPivotElementj = document.getElementById('pivot_' + row.j);
    
    this.currentDomPivotElementi.classList.add('markRed');
    this.currentDomPivotElementj.classList.add('markBlue');

  }
}
