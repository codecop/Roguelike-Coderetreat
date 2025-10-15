<?php
class RoomLayout
{
    public function serialize(): string
    {
        // Hardcoded 7x7 room with walls and one door
        $layout = [
            '#######',
            '#     #',
            '#     #',
            '#  |  #',
            '#     #',
            '#     #',
            '#######',
        ];
        return implode("\n", $layout) . "\n";
    }
}
