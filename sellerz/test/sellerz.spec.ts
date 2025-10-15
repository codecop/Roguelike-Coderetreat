import { Express } from 'express';
import { createApp } from '../src/app';
import request from 'supertest';

describe('Sellerz API', () => {
    let app: Express;

    beforeEach(async () => {
        app = await createApp();
    });

    it('renders initial map', async () => {
        const response = await request(app).
            get('/sellerz').
            expect(200);

        expect(response.header['content-type']).toBe('application/json; charset=utf-8');

        const mapResponse = response.body.layout;
        expect(mapResponse).toBeDefined();
        
        // Check if the body contains exactly one @ character
        const atCount = (mapResponse.match(/@/g) || []).length;
        expect(atCount).toBe(1);

        expect(mapResponse).toContain('|');

          // Check if the map has a maximum size of 15x15
        const rows = mapResponse.split('\n');
        expect(rows.length).toBeLessThanOrEqual(15);
        expect(rows.every((row: string) => row.length <= 15)).toBe(true);
    });

    it('moves the player', async () => {
        await request(app).
            post('/sellerz/walk?row=2&column=2').
            send().
            expect(201);

        const response = await request(app).
            get('/sellerz').
            expect(200);

        const mapResponse = response.body.layout;
        expect(mapResponse).toBeDefined();

        const rows = mapResponse.split('\n');
        expect(rows[2][2]).toBe('@');
    });
});