# Rogue-Runtime

This is a Python project. It needs TK. If you get an error that `TK` is not found, you need to add it to your standard libraries with

```bash
brew reinstall python-tk
```

## Install virtualenv & dependencies

```bash
pip3 install virtualenv
virtualenv -p python3 venv
source venv/bin/activate
pip3 install -r requirements.txt
```

## Run the tests

```bash
python3 -m unittest
```

## Run the UI

To start the application:

```bash
python3 main.py
```

You should see a room rendered in the UI:

![example-ui](./example-ui.png)
