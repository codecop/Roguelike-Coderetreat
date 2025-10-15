import Hello from '../src/Hello';

describe('Hello', () => {

    it('get', () => {
        const hello = new Hello();
        expect(hello.getName()).toBe('World!');
    });

});
