use thiserror::Error;

pub type Result<T> = std::result::Result<T, LanceNamespaceError>;

#[derive(Error, Debug)]
pub enum LanceNamespaceError {
    #[error("Operation not supported: {0}")]
    NotSupported(String),
    
    #[error("Unknown implementation: {0}")]
    UnknownImplementation(String),
    
    #[error("Invalid configuration: {0}")]
    InvalidConfiguration(String),
    
    #[error("Table not found: {0}")]
    TableNotFound(String),
    
    #[error("Namespace not found: {0}")]
    NamespaceNotFound(String),
    
    #[error("IO error: {0}")]
    Io(#[from] std::io::Error),
    
    #[error("OpenDAL error: {0}")]
    OpenDal(#[from] opendal::Error),
    
    #[error("Lance error: {0}")]
    Lance(#[from] lance::Error),
    
    #[error("Arrow error: {0}")]
    Arrow(#[from] arrow::error::ArrowError),
    
    #[error("Serde JSON error: {0}")]
    SerdeJson(#[from] serde_json::Error),
    
    #[error("HTTP error: {0}")]
    Http(#[from] reqwest::Error),
    
    #[error("URL parse error: {0}")]
    UrlParse(#[from] url::ParseError),
    
    #[error("Runtime error: {0}")]
    Runtime(String),
}