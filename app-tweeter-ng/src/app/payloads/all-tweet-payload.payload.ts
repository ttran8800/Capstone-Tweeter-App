import { ITweet } from "../models/tweet.model";

export interface AllTweetPayload {
    tweet: ITweet,
    loginId: string
}