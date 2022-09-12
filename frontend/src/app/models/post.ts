import { Comment } from './comment';
import { Reaction } from './reaction';

export interface Post {
  aggregateId: String;
  author: String;
  title: String;
  comments: Comment[];
  reaction: Reaction[];
}
