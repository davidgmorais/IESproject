export class Notifications {
  id: number;
  sender: number;
  receiver: number;
  created: Date;
  title: string;
  message: string;
  type: string;
  data: number;
  read: boolean;

  /*constructor(id: number, sender: number, receiver: number, created: Date, title: string,
              message: string, type: string, data: number, read: boolean) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
    this.created = created;
    this.title = title;
    this.message = message;
    this.type = type;
    this.data = data;
    this.read = read;
  }*/
  constructor(created: Date, read: boolean, message: string) {
    this.created = created;
    this.read = read;
    this.message = message;
  }
}
