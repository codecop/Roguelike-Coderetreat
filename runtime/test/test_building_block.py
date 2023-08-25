import unittest

class Wall:
    pass

def createBuildingBlockFromString(raw_string: str):
    return Wall() 

class BuildingBlockTest(unittest.TestCase):
    def test_wall(self):
        raw = '#'
        
        builing_block = createBuildingBlockFromString(raw)
    

        self.assertIsInstance(builing_block, Wall)

if __name__ == '__main__':
    unittest.main()