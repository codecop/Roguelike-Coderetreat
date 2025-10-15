import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';

describe('HelloApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('first Hello', async () => {
        const response = await request(app).
            get('/hello').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.name).toBe('World!');
    });
       it('should get room', async () => {
        const response = await request(app).
            get('/room').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe('######\n#    #\n#    |\n#    #\n######');
    });

    it('should update position', async () => {
        await request(app).
            post('/room/walk?row=2&column=2').
            send().
            expect(201);

        const { body } = await request(app).get('/room');
        // console.log(body.layout);
        expect(body.layout).toBe('######\n#    #\n# @  |\n#    #\n######');
    });

    it('updates', async () => {
        await request(app).
            post('/hello?name=Peter').
            send().
            expect(201);

        const { body } = await request(app).get('/hello');
        expect(body.name).toBe('Peter');
    });

});
