import { participation } from './participationtype';

export interface Countrytype {
  id: number,
  country: string,
  participations: participation[];
}
