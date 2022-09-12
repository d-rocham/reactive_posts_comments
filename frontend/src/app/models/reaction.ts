export interface Reaction {
  id: String;
  postId: String;
  author: String;
  // TODO: Should below property be an enum?
  reactionType: String;
}
