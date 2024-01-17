from src.app import app

port = 5000
print(f'Hello started on {port},')
print(f'Open http://localhost:{port}/hello')
app.run(port=port)
