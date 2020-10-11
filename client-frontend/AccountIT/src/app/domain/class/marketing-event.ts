export class MarketingEvent implements MarketingEvent {
  constructor(
    public id: number,
    public name: string,
    public date: Date,
    public address: string,
    public latitude: number,
    public longitude: number
  ) {}
}
