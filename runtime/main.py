from src.ui.ui import UI
from src.room_parser.room_parser import RoomParser


room = RoomParser().parse(
    """
      ##########
      #        #
      #        #
      # @      |
      #        #
      #        #
      #        #
      ##########
   """
)

ui = UI(room)
ui.run()
