import {Express} from 'express';
import {createApp} from '../src/app';
import request from 'supertest';
import Room from "../src/Room";

describe('HelloApp', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('first Room', async () => {
        const response = await request(app).get('/defaultRoom').expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');
        expect(response.body.layout).toBe(`#######
#     #
#@    #
#     #
#     #
#     #
#|#####
`);
    });

    it('updates player position', async () => {
        await request(app).post('/defaultRoom/walk?row=3&column=5')
            // .send({ "newPlayerPosition": [3, 5] })
            .expect(201);
    });

    it('default room with a character', () => {
        const room = new Room(5, 5);

        room.setNewPlayerPosition(1, 1);

        expect(room.toString()).toBe(`#######
#     #
# @   #
#     #
#     #
#     #
#|#####
`)
    });

});
