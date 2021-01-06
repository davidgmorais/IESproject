export class CinemaUser {
  userName: string;
  userEmail: string;
  password: string;
  role: number;
  avatar: string;
  location: string;
  description: string;


  constructor(userName: string, userEmail: string, password: string, location: string, description: string) {
    this.userName = userName;
    this.userEmail = userEmail;
    this.password = password;
    this.location = location;
    this.description = description;
  }
}
