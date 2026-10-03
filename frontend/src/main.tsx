import React,{useEffect,useState} from 'react';
import {createRoot} from 'react-dom/client';
import './styles.css';
const API=(import.meta as any).env.VITE_API_URL || 'http://localhost:8080/api';
type Exposure={counterpartyId:string,businessDate:string,currentExposure:number,pfe:number,ead:number,cva:number,collateral:number,approvedLimit:number,utilizationPct:number};
type Driver={driver:string,amount:number};
function money(n:number){return '$'+(n/1_000_000).toFixed(1)+'M'}
function App(){
 const [ex,setEx]=useState<Exposure|null>(null); const [drivers,setDrivers]=useState<Driver[]>([]);
 const [q,setQ]=useState('Why did ABC Bank exposure increase today?'); const [answer,setAnswer]=useState(''); const [evidence,setEvidence]=useState<string[]>([]); const [busy,setBusy]=useState(false); const [eventMsg,setEventMsg]=useState('');
 async function refresh(){const x=await fetch(API+'/counterparties/CP-001/exposure').then(r=>r.json());setEx(x);const d=await fetch(API+'/counterparties/CP-001/drivers?date='+x.businessDate).then(r=>r.json());setDrivers(d)}
 useEffect(()=>{refresh()},[]);
 async function bookTrade(){setBusy(true);setEventMsg('');const r=await fetch(API+'/demo/trades',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({counterpartyId:'CP-001',product:'Interest Rate Swap',notional:100000000,exposureImpact:12000000})});const j=await r.json();setEventMsg(j.message+' Event '+j.eventId.slice(0,8));setTimeout(refresh,1200);setBusy(false)}
 async function ask(){setBusy(true);setAnswer('');const r=await fetch(API+'/genai/ask',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({question:q,counterpartyId:'CP-001'})});const j=await r.json();setAnswer(j.answer);setEvidence(j.evidence||[]);setBusy(false)}
 return <div className="shell"><header><div><h1>Counterparty Credit Risk</h1><p>Event-driven CCR + governed GenAI analyst demo</p></div><span className="badge">CP-001 · ABC Bank</span></header>
 <main>{ex&&<><section className="grid">{[['Current Exposure',money(ex.currentExposure)],['PFE',money(ex.pfe)],['EAD',money(ex.ead)],['CVA',money(ex.cva)],['Collateral',money(ex.collateral)],['Limit Utilization',ex.utilizationPct+'%']].map(([k,v])=><div className="card" key={k}><span>{k}</span><strong>{v}</strong></div>)}</section>
 <section className="panel"><h2>1 · Book a demo trade</h2><p className="muted">Publishes a TRADE_BOOKED event. The CCR consumer recalculates the authoritative exposure snapshot.</p><div className="trade"><div><b>Interest Rate Swap</b><span>$100M notional · +$12M demo exposure impact</span></div><button onClick={bookTrade} disabled={busy}>Book Trade & Recalculate</button></div>{eventMsg&&<p className="success">{eventMsg}</p>}</section>
 <section className="panel"><h2>2 · Exposure drivers</h2><div className="drivers">{drivers.map((d,i)=><div key={i}><span>{d.driver}</span><b>{d.amount>=0?'+':''}{money(d.amount)}</b></div>)}</div></section></>}
 <section className="panel"><h2>3 · Ask the Risk Analyst Assistant</h2><p className="muted">The assistant explains governed CCR results; deterministic risk engines remain authoritative.</p><textarea value={q} onChange={e=>setQ(e.target.value)}/><button onClick={ask} disabled={busy}>{busy?'Working…':'Explain Exposure Change'}</button>{answer&&<div className="answer"><h3>Grounded response</h3><p>{answer}</p><h3>Evidence used</h3><ul>{evidence.map((x,i)=><li key={i}>{x}</li>)}</ul></div>}</section></main></div>
}
createRoot(document.getElementById('root')!).render(<App/>);
