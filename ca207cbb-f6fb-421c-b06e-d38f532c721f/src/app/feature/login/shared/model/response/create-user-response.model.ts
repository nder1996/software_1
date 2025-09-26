export class ResponseCreateUserModel {
    constructor(
        public id?: number,
        public name?: string,
        public job?: string,
        public createdAt?: Date
    ) {}
}