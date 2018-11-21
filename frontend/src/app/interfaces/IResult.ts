import { ITrace } from "./ITrace";

export interface IResult {
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
    pivotLogger: {
      pivotInformation: IPivotInformation[]
    }
  }
  
  export interface IPivotInformation {
    ptti: [{
      pivotListActivation: {
        pivots: [{
          i: number,
          j: number
        }],
        constraint: unknown
      },
      pivotListTarget: {
        pivots: [{
          i: number,
          j: number
        }],
        constraint: unknown
      }
    }],
    trace: ITrace
  }