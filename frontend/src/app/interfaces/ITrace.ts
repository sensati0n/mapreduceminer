export interface ITrace {
    events: [{
        attributes: [{
            key: string,
            value: string
        }]
    }]
}