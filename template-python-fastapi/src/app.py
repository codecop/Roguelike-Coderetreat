from fastapi import FastAPI, Query, Response, status
from fastapi.responses import JSONResponse
from typing import Optional
from src.hello import Hello

app = FastAPI()

# Single shared Hello instance (not thread-safe)
hello = Hello()


@app.get("/hello")
async def get_hello():
    return JSONResponse(content={"name": hello.getName()}, status_code=200)


@app.post("/hello")
async def post_hello(name: Optional[str] = Query(None), response: Response = None):
    if name is None or name == "":
        response.status_code = status.HTTP_400_BAD_REQUEST
        return JSONResponse(content={}, status_code=400)

    hello.setName(name)
    response.status_code = status.HTTP_201_CREATED
    return JSONResponse(content={}, status_code=201)
