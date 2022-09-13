export interface Reaction {
  id: string;
  postId: string;
  author: string;
  // TODO: Should below property be an enum?
  reactionType: string;
}
