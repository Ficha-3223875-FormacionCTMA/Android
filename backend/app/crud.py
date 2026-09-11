from sqlalchemy.orm import Session
from app import schemas
from app.models.actividad import Actividad


def get_actividades(db: Session, skip: int = 0, limit: int = 100) -> list[Actividad]:
    return (
        db.query(Actividad)
        .order_by(
            Actividad.prioridad == "Alta",
            Actividad.prioridad == "Media",
            Actividad.fecha.desc()
        )
        .offset(skip)
        .limit(limit)
        .all()
    )


def get_actividad(db: Session, actividad_id: int) -> Actividad | None:
    return db.query(Actividad).filter(Actividad.id == actividad_id).first()


def create_actividad(db: Session, actividad: schemas.ActividadCreate) -> Actividad:
    nueva = Actividad(**actividad.model_dump())
    db.add(nueva)
    db.commit()
    db.refresh(nueva)
    return nueva


def update_actividad(
    db: Session, actividad_id: int, actividad: schemas.ActividadUpdate
) -> Actividad | None:
    existente = get_actividad(db, actividad_id)
    if not existente:
        return None

    for campo, valor in actividad.model_dump(exclude_unset=True).items():
        setattr(existente, campo, valor)

    db.commit()
    db.refresh(existente)
    return existente


def delete_actividad(db: Session, actividad_id: int) -> bool:
    existente = get_actividad(db, actividad_id)
    if not existente:
        return False

    db.delete(existente)
    db.commit()
    return True