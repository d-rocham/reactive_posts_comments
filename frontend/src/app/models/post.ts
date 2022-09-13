import { Comment } from './comment';
import { Reaction } from './reaction';

export interface Post {
  aggregateId: string;
  author: string;
  title: string;
  comments: Comment[];
  reactions: Reaction[];
}
