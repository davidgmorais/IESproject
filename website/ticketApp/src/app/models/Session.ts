export class Session {
  sessionDate: Date;    // both date and time
  room: string;

  constructor(sessionDate: Date, room: string) {
    this.sessionDate = sessionDate;
    this.room = room;
  }
}
