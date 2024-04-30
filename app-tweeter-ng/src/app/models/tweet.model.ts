
export interface ITweet {
    tweetId?: number,
    date: Date,
    message: string,
    userId: number,
    parentTweetId: number | null
}