import unittest
from unittest.mock import patch, Mock
import src.game_service.game_service

GameService = src.game_service.game_service.GameService


class GameServiceTests(unittest.TestCase):
    @patch("src.game_service.game_service.requests.get")
    def test_get_room_should_fetch_and_update_room(self, mock_get):
        mock_response = Mock()
        mock_response.json = lambda: {
            "layout": "###\n#@|\n###",
            "description": "Bla bla bla...",
        }
        mock_get.return_value = mock_response

        mock_game = Mock()

        game_service = GameService(mock_game)
        game_service.get_room()

        mock_game.update_room.assert_called_once_with(
            {
                "layout": "###\n#@|\n###",
                "description": "Bla bla bla...",
            }
        )

    @patch("src.game_service.game_service.requests.get")
    def test_get_room_should_fail_silently(self, mock_get):
        mock_response = Mock()

        def mock_fn():
            raise Exception()

        mock_response.json = mock_fn
        mock_get.return_value = mock_response
        mock_game = Mock()

        try:
            game_service = GameService(mock_game)
            game_service.get_room()
        except:
            raise AssertionError(f"Expected exception not to be raised.")

    @patch("src.game_service.game_service.requests.get")
    def test_get_stats_should_fetch_and_update_stats(self, mock_get):
        mock_response = Mock()
        mock_response.json = lambda: {"mock": "mock"}
        mock_get.return_value = mock_response

        mock_game = Mock()

        game_service = GameService(mock_game)
        game_service.get_stats()

        mock_game.update_stats.assert_called_once_with({"mock": "mock"})
