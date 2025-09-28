import uvicorn
from src.app import app

if __name__ == "__main__":
    port = 6000
    print(f"Hello started on {port},")
    print(f"Open http://localhost:{port}/hello")
    uvicorn.run(app, host="0.0.0.0", port=port)
