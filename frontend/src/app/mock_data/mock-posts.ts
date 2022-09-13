import { Post } from '../models/post';

export const POSTS: Post[] = [
  {
    aggregateId: '01',
    author: 'Daniel',
    title: 'Mock post title',
    comments: [
      {
        id: '01',
        postId: '01',
        author: 'Daniel',
        content: 'Mock post content',
      },
    ],
    reactions: [
      {
        id: '01',
        postId: '01',
        author: 'Daniel',
        reactionType: 'LOL',
      },
    ],
  },
  {
    aggregateId: '02',
    author: 'Frampt',
    title: 'Chosen undead mission',
    comments: [
      {
        id: '01',
        postId: '01',
        author: 'Kaathe',
        content: 'This is bs',
      },
    ],
    reactions: [
      {
        id: '01',
        postId: '01',
        author: 'Gwin',
        reactionType: 'LOL',
      },
    ],
  },
];
