# How to run

## Install virtualenv & dependencies

```bash
pip3 install virtualenv
virtualenv -p python3 venv
source venv/bin/activate
pip3 install -r requirements.txt
```

## Run the web server & UI

```bash
python3 main.py
```

```bash
 * Serving Flask app 'main'
 * Debug mode: off
WARNING: This is a development server. Do not use it in a production deployment. Use a production WSGI server instead.
 * Running on http://127.0.0.1:5000
```

(also a window with blank UI should show)

## Example of an API request

```zsh
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"room":"###\n#@|\n###"}' \
  http://localhost:5000/input
```

You should see a room rendered in the UI:

![example-ui](./example-ui.png)