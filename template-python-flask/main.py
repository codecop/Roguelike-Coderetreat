from src.app import app

if __name__ == "__main__":
    port = 5000
    print(f"Hello started on {port},")
    print(f"Open http://localhost:{port}/hello")
    app.run(port=port)
