export class UserComment {

  id: number;
  parentId: number;
  author: number;
  cinema: number;
  content: string;
  created: Date;
  likes: number;
  film: number;
  premier: number;
  replyTo: number;
  flag: boolean;


  constructor(id: number, author: number, content: string, created: Date) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.created = created;
  }
}
