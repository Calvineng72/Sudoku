import styles from '../styles/Home.module.css'
import createGameEnvironment from '../components/SudokuBoard'
import { useEffect } from 'react'

export default function Home() {
  useEffect(() => {
    createGameEnvironment(81);
  }, []);
  
  return (
    <div className={styles.container}>
      <div id='puzzle' className='puzzle'>
      </div>
    </div>
  )
}