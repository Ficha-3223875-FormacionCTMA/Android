from contextlib import asynccontextmanager
from fastapi import FastAPI
from app.routers import actividades
from app.models.actividad import Actividad
from app.database import Base, engine


@asynccontextmanager
async def lifespan(app: FastAPI):
    Base.metadata.create_all(bind=engine)
    yield


app = FastAPI(
    title="Mi Formacion CTMA API",
    description="API para gestionar las actividades de formacion",
    version="1.0.0",
    lifespan=lifespan
)

app.include_router(actividades.router)


@app.get("/")
def root():
    return {"mensaje": "Mi Formacion CTMA API funcionando"}


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)