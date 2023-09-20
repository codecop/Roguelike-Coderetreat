import tkinter as tk
from src.ui.room_ui import RoomUI


class LogUI(tk.Frame):
    def __init__(self, parent, max_lines=5):
        super().__init__(parent, bg="black", width=RoomUI.width)
        self.max_lines = max_lines
        self.lines = []
        self.label_widgets = []
        self.label_count = 0
        self.setup_ui()

    def setup_ui(self):
        for _ in range(self.max_lines):
            label = tk.Label(
                self,
                bg="black",
                fg="white",
                justify="left",
                anchor="w",
                padx=20,
                pady=5,
                font=("Courier New", 18),
            )
            label.pack(fill="both")
            self.label_widgets.append(label)

    def reset(self):
        self.lines = []

    def add_line(self, line):
        if len(self.lines) >= self.max_lines:
            self.lines.pop(0)
        self.lines.append(line)

    def draw(self):
        for i, label in enumerate(self.label_widgets):
            if i < len(self.lines):
                label.config(text=self.lines[i])
            else:
                label.config(text="")
