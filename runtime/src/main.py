from tkinter import *
from PIL import Image, ImageTk

win = Tk()
win.geometry("710x710")

canvas = Canvas(win, width=700, height=700, bg="white")
canvas.pack(pady=20)

image = Image.open('favicon.png')
resized_image = image.resize((50, 50))
new_image = ImageTk.PhotoImage(resized_image)

img = canvas.create_image(250, 120, anchor=NW, image=new_image)

def left(e):
   x = -20
   y = 0
   canvas.move(img, x, y)

def right(e):
   x = 20
   y = 0
   canvas.move(img, x, y)

def up(e):
   x = 0
   y = -20
   canvas.move(img, x, y)

def down(e):
   x = 0
   y = 20
   canvas.move(img, x, y)

def bind():
    win.bind("<Left>", left)
    win.bind("<Right>", right)
    win.bind("<Up>", up)
    win.bind("<Down>", down)


bind()
win.mainloop()