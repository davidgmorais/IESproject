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


  constructor(author: number, content: string, created: Date) {
    this.author = author;
    this.content = content;
    this.created = created;
  }
}
