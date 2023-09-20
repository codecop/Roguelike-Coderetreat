import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';

describe('HelloApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('first Room', async () => {
        const response = await request(app).
            get('/defaultRoom').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe(`###
# #
#|#
`);
    });

    // it('updates', async () => {
    //     await request(app).
    //         post('/hello').
    //         send({ "name": "Peter" }).
    //         expect(201);
    //
    //     const { body } = await request(app).get('/hello');
    //     expect(body.name).toBe('Peter');
    // });

});
