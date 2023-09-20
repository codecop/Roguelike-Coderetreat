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

    it('updates player position', async () => {
        await request(app).
            post('/defaultRoom/walk?row=3&column=5')
            // .send({ "newPlayerPosition": [3, 5] })
            .expect(201);
    });

});
