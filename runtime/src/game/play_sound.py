from tkinter import *


def play_beep():
    try:
        from AppKit import NSBeep

        NSBeep()  # Plays the macOS default system alert sound
    except ImportError:
        print("Beep sound is not supported without AppKit.")
        pass
