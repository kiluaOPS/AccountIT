import { IMarketingEvent } from './imarketing-event';

export class MarketingEvent implements IMarketingEvent {
    id: number;
    name: string;
    date: Date;
    address: string;
    latitude: number;
    longitude: number;
  constructor(
  ) {}
}
