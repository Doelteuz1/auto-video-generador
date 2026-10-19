import React, { useState } from 'react';
import { videoService } from './services/videoService';

export default function App() {
  const [topic, setTopic] = useState('');
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!topic.trim()) return;

    setLoading(true);
    setError(null);
    setResult(null);

    try {
      // Envía el objeto que mapea con tu VideoRequest.java
      const data = await videoService.generateVideo({ topic });
      setResult(data);
    } catch (err) {
      console.error(err);
      setError('Hubo un error al conectar con el servidor de Spring Boot.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '40px auto', fontFamily: 'sans-serif', padding: '20px' }}>
      <h1>🎬 Auto Video Generator</h1>
      <p style={{ color: '#666' }}>Conectado con tu backend de Spring Boot</p>

      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '15px', marginTop: '20px' }}>
        <div>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Tema o Prompt del Video:
          </label>
          <textarea
            rows="4"
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
            placeholder="Escribe de qué quieres que sea el video..."
            style={{ width: '100%', padding: '10px', borderRadius: '6px', border: '1px solid #ccc' }}
            disabled={loading}
            required
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          style={{
            padding: '12px',
            backgroundColor: loading ? '#94a3b8' : '#2563eb',
            color: '#fff',
            border: 'none',
            borderRadius: '6px',
            cursor: loading ? 'not-allowed' : 'pointer',
            fontWeight: 'bold',
          }}
        >
          {loading ? 'Generando (Guion ➔ Audio ➔ Video)...' : 'Generar Video'}
        </button>
      </form>

      {loading && (
        <div style={{ marginTop: '20px', padding: '15px', backgroundColor: '#eff6ff', borderRadius: '6px', color: '#1e40af' }}>
          ⏳ Tu backend de Spring Boot está procesando la solicitud. Esto puede tomar unos momentos...
        </div>
      )}

      {error && (
        <div style={{ marginTop: '20px', padding: '15px', backgroundColor: '#fef2f2', borderRadius: '6px', color: '#991b1b' }}>
          {error}
        </div>
      )}

      {result && (
        <div style={{ marginTop: '20px', padding: '15px', backgroundColor: '#f0fdf4', borderRadius: '6px', color: '#166534' }}>
          <h3>¡Resultado Exitoso!</h3>
          <pre style={{ background: '#fff', padding: '10px', borderRadius: '4px', overflowX: 'auto' }}>
            {JSON.stringify(result, null, 2)}
          </pre>
        </div>
      )}
    </div>
  );
}