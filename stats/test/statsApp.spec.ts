import { describe, it } from 'mocha';
import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';
import { expect } from 'chai';

describe('Stats App', () => {

    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('get HP', async () => {
        const response = await request(app).
            get('/stats/hp').
            expect(200);

        expect(response.header['content-type']).equal('application/json; charset=utf-8');
        const body = response.body;
        expect(body.hp).equal(10);
        expect(body.alive).equal(true);
    });

    it('update HP', async () => {
        await request(app).
            post('/stats/hp').
            query('action=hit').
            expect(201);

        const { body: bodyDecreased } = await request(app).get('/stats/hp');
        expect(bodyDecreased.hp).equal(9);

        await request(app).
            post('/stats/hp').
            query('action=heal').
            expect(201);

        const { body: bodyIncreased } = await request(app).get('/stats/hp');
        expect(bodyIncreased.hp).equal(10);
    });

    it('dynamic attribute level', async () => {
        const { body } = await request(app).get('/stats/level');
        expect(body.level).equal(0);

        await request(app).
            post('/stats/level').
            query('action=inc').
            expect(201);

        const { body: bodyIncreased } = await request(app).get('/stats/level');
        expect(bodyIncreased.level).equal(1);

        await request(app).
            del('/stats/level').
            expect(202);

        const { body: bodyReset } = await request(app).get('/stats/level');
        expect(bodyReset.level).equal(0);
    });

});
